package br.edu.helpdesk;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class ChamadoControllerTest {
 @Autowired MockMvc mvc; @Autowired ChamadoService service;
 @BeforeEach void reset(){service.reset();}
 static final String URL="/api/chamados";
 static final String VALID="""
 {"solicitante":"Davi","descricao":"Falha","categoria":"REDE","prioridade":"ALTA","status":"RESOLVIDO"}
 """;
 @Test void health() throws Exception {mvc.perform(get("/api/health")).andExpect(status().isOk());}
 @Test void g1Criar() throws Exception {mvc.perform(post(URL).contentType("application/json").content(VALID)).andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(4));}
 @Test void g1StatusInicial() throws Exception {mvc.perform(post(URL).contentType("application/json").content(VALID)).andExpect(jsonPath("$.status").value("ABERTO"));}
 @Test void g1SemNome() throws Exception {mvc.perform(post(URL).contentType("application/json").content(VALID.replace("Davi"," "))).andExpect(status().isBadRequest());}
 @Test void g1SemDescricao() throws Exception {mvc.perform(post(URL).contentType("application/json").content(VALID.replace("Falha",""))).andExpect(status().isBadRequest());}
 @Test void g1Prioridade() throws Exception {mvc.perform(post(URL).contentType("application/json").content(VALID.replace("ALTA","URGENTE"))).andExpect(status().isBadRequest());}
 @Test void g2Todos() throws Exception {mvc.perform(get(URL)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(3));}
 @Test void g2Abertos() throws Exception {mvc.perform(get(URL).param("status","ABERTO")).andExpect(jsonPath("$.length()").value(2));}
 @Test void g2Resolvidos() throws Exception {mvc.perform(get(URL).param("status","RESOLVIDO")).andExpect(jsonPath("$[0].id").value(3));}
 @Test void g2Invalido() throws Exception {mvc.perform(get(URL).param("status","OUTRO")).andExpect(status().isBadRequest());}
 @Test void g2Vazio() throws Exception {mvc.perform(get(URL).param("status","")).andExpect(jsonPath("$.length()").value(3));}
 @Test void g3Resolver() throws Exception {mvc.perform(patch(URL+"/1/status").contentType("application/json").content("{\"status\":\"RESOLVIDO\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("RESOLVIDO"));}
 @Test void g3Reabrir() throws Exception {mvc.perform(patch(URL+"/3/status").contentType("application/json").content("{\"status\":\"ABERTO\"}")).andExpect(jsonPath("$.status").value("ABERTO"));}
 @Test void g3Inexistente() throws Exception {mvc.perform(patch(URL+"/99/status").contentType("application/json").content("{\"status\":\"ABERTO\"}")).andExpect(status().isNotFound());}
 @Test void g3Invalido() throws Exception {mvc.perform(patch(URL+"/1/status").contentType("application/json").content("{\"status\":\"X\"}")).andExpect(status().isBadRequest());}
 @Test void g3Ausente() throws Exception {mvc.perform(patch(URL+"/1/status").contentType("application/json").content("{}")).andExpect(status().isBadRequest());}
 @Test void g4Excluir() throws Exception {mvc.perform(delete(URL+"/1")).andExpect(status().isNoContent());}
 @Test void g4Inexistente() throws Exception {mvc.perform(delete(URL+"/99")).andExpect(status().isNotFound());}
 @Test void g4Repetir() throws Exception {mvc.perform(delete(URL+"/1"));mvc.perform(delete(URL+"/1")).andExpect(status().isNotFound());}
 @Test void g4Colecao() throws Exception {mvc.perform(delete(URL+"/2"));Assertions.assertEquals(2,service.listar(null).size());}
 @Test void g4SemCorpo() throws Exception {mvc.perform(delete(URL+"/3")).andExpect(status().isNoContent()).andExpect(content().string(""));}
 @Test void g5Total() throws Exception {mvc.perform(get(URL+"/estatisticas")).andExpect(status().isOk()).andExpect(jsonPath("$.total").value(3));}
 @Test void g5Abertos() throws Exception {mvc.perform(get(URL+"/estatisticas")).andExpect(jsonPath("$.abertos").value(2));}
 @Test void g5Resolvidos() throws Exception {mvc.perform(get(URL+"/estatisticas")).andExpect(jsonPath("$.resolvidos").value(1));}
 @Test void g5Alta() throws Exception {mvc.perform(get(URL+"/estatisticas")).andExpect(jsonPath("$.alta").value(1));}
 @Test void g5Dinamico() throws Exception {service.excluir(1);mvc.perform(get(URL+"/estatisticas")).andExpect(jsonPath("$.total").value(2)).andExpect(jsonPath("$.alta").value(0));}
}
