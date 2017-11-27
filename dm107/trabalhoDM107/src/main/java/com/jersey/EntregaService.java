package com.jersey;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/apiEntrega")
public class EntregaService {
	@GET
	@Path("/{numPedido}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listEntregaById(@PathParam("numPedido") int numPedido) {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		EntregaModel entrega = null;
		try {
			Connection conn = connectionFactory.getConnection();
			EntregaDAO EntregaDAO = new EntregaDAO();
			entrega = EntregaDAO.listByNumPedido(conn, numPedido);

			if (entrega.getId() == 0) {
				return Response.status(Response.Status.NOT_FOUND).build();
			} else {
				return Response.status(Response.Status.OK).entity(entrega).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(EntregaModel entrega) {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		if (entrega.getNumPedido() == 0 || entrega.getIdCliente() == 0) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		try {
			Connection conn = connectionFactory.getConnection();
			EntregaDAO entregaDAO = new EntregaDAO();
			entregaDAO.insert(conn, entrega);
			return Response.status(Response.Status.OK).entity(entrega).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

}
