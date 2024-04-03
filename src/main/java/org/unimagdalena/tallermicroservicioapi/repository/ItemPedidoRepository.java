package org.unimagdalena.tallermicroservicioapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;

import java.util.List;
import java.util.UUID;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, UUID> {

    //Buscar Items del pedido por Pedido Id
    List<ItemPedido> findByPedidoId(UUID pedidoId);

    //Buscar items del pedido para un producto específico
    List<ItemPedido> findByProductId(UUID productId);

    //Calcular la suma del total de ventas para un producto, utilice la agregación SUM
    @Query("SELECT SUM(ip.cantidad * ip.precioUnitario) FROM ItemPedido ip WHERE ip.product.id = ?1")
    Float getTotalVentasByProductId(UUID productId);

}
