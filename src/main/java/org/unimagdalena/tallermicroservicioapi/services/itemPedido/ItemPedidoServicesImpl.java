package org.unimagdalena.tallermicroservicioapi.services.itemPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.itemPedido.ItemPedidoToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.ItemPedido;
import org.unimagdalena.tallermicroservicioapi.entities.Pedido;
import org.unimagdalena.tallermicroservicioapi.entities.Product;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.mappers.ItemPedidoMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ItemPedidoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.PedidoRepository;
import org.unimagdalena.tallermicroservicioapi.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemPedidoServicesImpl implements  ItemPedidoServices{

    ItemPedidoRepository itemPedidoRepository;
    PedidoRepository pedidoRepository;
    ProductRepository productRepository;
    ItemPedidoMapper itemPedidoMapper;

    @Autowired
    public ItemPedidoServicesImpl(
            ItemPedidoRepository itemPedidoRepository,
            ItemPedidoMapper itemPedidoMapper,
            PedidoRepository pedidoRepository,
            ProductRepository productRepository
    ) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.itemPedidoMapper = itemPedidoMapper;
        this.pedidoRepository = pedidoRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ItemPedidoToShowDto getItemPedidoById(UUID id) {

        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(id);

        if(itemPedidoConsultado.isEmpty())
            throw new NotFoundException("ItemPedido con ID " + id + " no encontrado");

        return itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto( itemPedidoConsultado.get() );
    }

    @Override
    public List<ItemPedidoToShowDto> getAllItemPedido() {

        List<ItemPedido> todosLosItemPedido = itemPedidoRepository.findAll();

        if(todosLosItemPedido.isEmpty())
            throw new NotFoundException("No hay ItemPedido en la base de datos");

        List<ItemPedidoToShowDto> itemPedidoARegresar = new ArrayList<>();

        todosLosItemPedido.forEach(itemPedido -> {
            itemPedidoARegresar.add(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(itemPedido));
        });


        return itemPedidoARegresar;
    }

    @Override
    public ItemPedidoToShowDto saveItemPedido(ItemPedidoToSaveDto itemPedidoToSaveDto) {
        ItemPedido itemPedido = itemPedidoMapper.itemPedidoToSaveDtoToItemPedidoEntity(itemPedidoToSaveDto);

        UUID pedidoId = itemPedidoToSaveDto.pedido().id();
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido con ID " + pedidoId + " no encontrado"));

        UUID productId = itemPedidoToSaveDto.product().id();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Producto con ID " + productId + " no encontrado"));

        itemPedido.setPedido(pedido);
        itemPedido.setProduct(product);

        ItemPedido itemPedidoGuardado = itemPedidoRepository.save(itemPedido);

        return itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(itemPedidoGuardado);
    }

    @Override
    public ItemPedidoToShowDto updateItemPedido(UUID itemPedidoId, ItemPedidoToSaveDto itemPedidoToSaveDto) {

        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(itemPedidoId);

        if(itemPedidoConsultado.isEmpty())
            throw new NotFoundException("ItemPedido con ID " + itemPedidoId + " no encontrado");

        ItemPedido itemPedido = itemPedidoConsultado.get();

        if (itemPedidoToSaveDto.cantidad() != null) itemPedido.setCantidad(itemPedidoToSaveDto.cantidad());
        if (itemPedidoToSaveDto.precioUnitario() != null) itemPedido.setPrecioUnitario(itemPedidoToSaveDto.precioUnitario());

        if(itemPedidoToSaveDto.pedido() != null && itemPedidoToSaveDto.pedido().id() != null){
            UUID pedidoId = itemPedidoToSaveDto.pedido().id();
            Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
            pedido.ifPresent(itemPedido::setPedido);
        }

        if(itemPedidoToSaveDto.product() != null && itemPedidoToSaveDto.product().id() != null){
            UUID productId = itemPedidoToSaveDto.product().id();
            Optional<Product> product = productRepository.findById(productId);
            product.ifPresent(itemPedido::setProduct);
        }

        ItemPedido itemPedidoActualizado = itemPedidoRepository.save(itemPedido);

        return itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(itemPedidoActualizado);
    }

    @Override
    public List<ItemPedidoToShowDto> findItemPedidoToShowDtoByPedidoId(UUID pedidoId) {

        List<ItemPedido> itemPedido = itemPedidoRepository.findByPedidoId(pedidoId);

        if(itemPedido.isEmpty())
            throw new NotFoundException("ItemPedido con Pedido ID " + pedidoId + " no encontrado");

        List<ItemPedidoToShowDto> itemPedidoARegresar = new ArrayList<>();

        itemPedido.forEach(item -> {
            itemPedidoARegresar.add(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(item));
        });

        return itemPedidoARegresar;

    }

    @Override
    public List<ItemPedidoToShowDto> findItemPedidoToShowDtoByProductId(UUID productId) {

        List<ItemPedido> itemPedido = itemPedidoRepository.findByProductId(productId);

        if(itemPedido.isEmpty())
            throw new NotFoundException("ItemPedido con Producto ID " + productId + " no encontrado");

        List<ItemPedidoToShowDto> itemPedidoARegresar = new ArrayList<>();

        itemPedido.forEach(item -> {
            itemPedidoARegresar.add(itemPedidoMapper.itemPedidoEntityToItemPedidoToShowDto(item));
        });

        return itemPedidoARegresar;

    }

    @Override
    public void deleteItemPedidoById(UUID id) {

        Optional<ItemPedido> itemPedidoConsultado = itemPedidoRepository.findById(id);

        if(itemPedidoConsultado.isEmpty())
            throw new NotFoundException("ItemPedido con ID " + id + " no encontrado");

        itemPedidoRepository.deleteById(id);

    }

}
