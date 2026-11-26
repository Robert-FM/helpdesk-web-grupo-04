package br.edu.helpdesk;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class ChamadoService {
 private final Map<Long,Chamado> dados = new LinkedHashMap<>();
 private long proximo = 4;
 public ChamadoService() { reset(); }
 public synchronized void reset() {
  dados.clear(); proximo=4;
  dados.put(1L,new Chamado(1,"Ana","Computador não liga","HARDWARE","ALTA","ABERTO"));
  dados.put(2L,new Chamado(2,"Bruno","Falha na rede","REDE","MEDIA","ABERTO"));
  dados.put(3L,new Chamado(3,"Carla","Instalar editor","SOFTWARE","BAIXA","RESOLVIDO"));
 }
 public synchronized List<Chamado> listar(String status) { return dados.values().stream().filter(c -> status==null || status.isBlank() || c.status().equals(status)).toList(); }
 public synchronized Chamado criar(Chamado entrada) {
  var c=new Chamado(proximo++,entrada.solicitante().trim(),entrada.descricao().trim(),entrada.categoria(),entrada.prioridade(),"ABERTO"); dados.put(c.id(),c); return c;
 }
 public synchronized Chamado atualizar(long id,String status) {
  var c=dados.get(id); if(c==null) return null;
  var novo=new Chamado(id,c.solicitante(),c.descricao(),c.categoria(),c.prioridade(),status); dados.put(id,novo); return novo;
 }
 public synchronized boolean excluir(long id) { return dados.remove(id)!=null; }
 public synchronized Map<String,Long> estatisticas() {
  return Map.of("total",(long)dados.size(),"abertos",dados.values().stream().filter(c->c.status().equals("ABERTO")).count(),"resolvidos",dados.values().stream().filter(c->c.status().equals("RESOLVIDO")).count(),"alta",dados.values().stream().filter(c->c.prioridade().equals("ALTA")).count());
 }
}
