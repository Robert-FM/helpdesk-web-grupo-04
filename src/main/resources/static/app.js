const $ = id => document.getElementById(id);
async function api(url, options={}) {
 const r=await fetch(url,options);
 if(r.status===204) return null;
 const data=await r.json();
 if(!r.ok) throw new Error(data.mensagem || `HTTP ${r.status}`);
 return data;
}
const json=(method,data)=>({method,headers:{"Content-Type":"application/json"},body:JSON.stringify(data)});
function mensagem(texto){$("mensagem").textContent=texto;}
async function criar(evento){
 evento.preventDefault();
 const dados=Object.fromEntries(new FormData(evento.target));
 try { await api("/api/chamados",json("POST",dados)); evento.target.reset(); mensagem("Chamado aberto com sucesso."); await atualizarTela(); } catch(e){mensagem(e.message);}
}
async function listar(){
 const status=$("filtro").value;
 const chamados=await api("/api/chamados"+(status?"?status="+encodeURIComponent(status):""));
 renderizar(chamados);
}
function renderizar(chamados){
 const lista=$("lista"); lista.replaceChildren();
 for(const c of chamados){
  const tr=document.createElement("tr");
  for(const valor of [c.id,c.solicitante,c.descricao,c.prioridade,c.status]){const td=document.createElement("td");td.textContent=valor;tr.append(td);}
  const td=document.createElement("td");
  const alterar=document.createElement("button"); alterar.textContent=c.status==="ABERTO"?"Resolver":"Reabrir"; alterar.onclick=()=>alterarStatus(c);
  const excluir=document.createElement("button"); excluir.textContent="Excluir"; excluir.onclick=()=>excluirChamado(c.id);
  td.append(alterar,excluir);tr.append(td);lista.append(tr);
 }
}
async function alterarStatus(c){
 const status=c.status==="ABERTO"?"RESOLVIDO":"ABERTO";
 try {await api(`/api/chamados/${c.id}/status`,json("PATCH",{status}));mensagem("Status atualizado.");await atualizarTela();}catch(e){mensagem(e.message);}
}
async function excluirChamado(id){
 // TODO 4: Use service.excluir e retorne 204 sem corpo ou 404. No JS confirme com confirm e envie DELETE; atualize a tela.
 mensagem("Funcionalidade pendente.");
}
async function indicadores(){
 const dados=await api("/api/chamados/estatisticas");
 for(const chave of ["total","abertos","resolvidos","alta"]) $(chave).textContent=dados[chave];
}
async function atualizarTela(){
 $("lista-erro").textContent="";
 const resultados=await Promise.allSettled([listar(),indicadores()]);
 $("lista-erro").textContent=resultados.filter(r=>r.status==="rejected").map(r=>r.reason.message).join(" / ");
}
$("form-chamado").addEventListener("submit",criar);
$("filtro").addEventListener("change",atualizarTela);
atualizarTela();
