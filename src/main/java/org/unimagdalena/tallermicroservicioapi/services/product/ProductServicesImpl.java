package org.unimagdalena.tallermicroservicioapi.services.product;

import org.springframework.stereotype.Service;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToSaveDto;
import org.unimagdalena.tallermicroservicioapi.dto.product.ProductToShowDto;
import org.unimagdalena.tallermicroservicioapi.entities.Product;
import org.unimagdalena.tallermicroservicioapi.exception.NotFoundException;
import org.unimagdalena.tallermicroservicioapi.mappers.ProductMapper;
import org.unimagdalena.tallermicroservicioapi.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServicesImpl implements ProductServices{

    ProductRepository productRepository;
    ProductMapper productMapper;

    public ProductServicesImpl(ProductRepository productRepository,  ProductMapper productMapper){
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductToShowDto> findAllProducts() {

        List<Product> products = productRepository.findAll();

        if (products.isEmpty())
            throw new NotFoundException("No hay productos registrado");

        List<ProductToShowDto> productosADevolver = new ArrayList<>();

        products.forEach( product -> {
            ProductToShowDto p = productMapper.productEntityToProductToShowDto(product);
            productosADevolver.add(p);
        });

        return productosADevolver;
    }

    @Override
    public ProductToShowDto findProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty())
            throw new NotFoundException("Producto con ID " + id + " no encontrado");

        return productMapper.productEntityToProductToShowDto(product.get());
    }

    @Override
    public List<ProductToShowDto> findProductByNombre(String nombre) {
        List<Product> productMatch = productRepository.findByNombreContainingIgnoreCase(nombre);

        if (productMatch.isEmpty())
            throw new NotFoundException("No se encontró productos que contengan " + nombre);

        List<ProductToShowDto> productosARegresar = new ArrayList<>();

        productMatch.forEach(product -> {
            ProductToShowDto productMappeado = productMapper.productEntityToProductToShowDto(product);
            productosARegresar.add(productMappeado);
        });

        return productosARegresar;
    }

    @Override
    public List<ProductToShowDto> findProductInStock() {
        List<Product> productosEnStock = productRepository.findInStock();

        if (productosEnStock.isEmpty())
            throw new NotFoundException("No hay productos en stock");

        List<ProductToShowDto> productosARegresar = new ArrayList<>();

        productosEnStock.forEach(product -> {
            ProductToShowDto productMappeado = productMapper.productEntityToProductToShowDto(product);
            productosARegresar.add(productMappeado);
        });

        return productosARegresar;
    }

    @Override
    public ProductToShowDto saveProduct(ProductToSaveDto product) {
        Product productoAGuardar = productMapper.productToSaveDtoToProductEntity(product);
        Product productoGuardado = productRepository.save(productoAGuardar);
        return productMapper.productEntityToProductToShowDto(productoGuardado);
    }

    @Override
    public ProductToShowDto updateProductById(UUID id, ProductToSaveDto product) {
        Optional<Product> productConsultado = productRepository.findById(id);

        if (productConsultado.isEmpty())
            throw new NotFoundException("Producto con ID " + id + " no encontrado");

        Product pr = productConsultado.get();

        if (product.nombre() != null) pr.setNombre(product.nombre());
        if (product.stock() != null) pr.setStock(product.stock());
        if (product.price() != null) pr.setPrice(product.price());

        Product productActualizado = productRepository.save(pr);

        return productMapper.productEntityToProductToShowDto(productActualizado);
    }

    @Override
    public void deleteProductById(UUID id) {
        Optional<Product> productAEliminar = productRepository.findById(id);

        if (productAEliminar.isEmpty())
            throw new NotFoundException("Producto con ID " + id + " no existe");

        productRepository.deleteById(id);
    }
}
