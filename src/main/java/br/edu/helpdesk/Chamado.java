package br.edu.helpdesk;
public record Chamado(long id, String solicitante, String descricao, String categoria, String prioridade, String status) {}
