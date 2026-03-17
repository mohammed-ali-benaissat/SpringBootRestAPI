package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.ProductDto;
import com.self.springbootprojectstarter.model.Product;
import com.self.springbootprojectstarter.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProduct_returnsSavedDto() {
        // Arrange: mapper converts DTO to entity, repository returns saved entity.
        ProductDto dto = new ProductDto("pencil");
        Product entity = new Product();
        Product saved = new Product();
        saved.setId(1L);
        saved.setName("pencil");
        ProductDto savedDto = new ProductDto("pencil");
        savedDto.setId(1L);

        when(modelMapper.map(dto, Product.class)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(saved);
        when(modelMapper.map(saved, ProductDto.class)).thenReturn(savedDto);

        // Act: create product.
        ProductDto result = productService.addProduct(dto);

        // Assert: returned DTO reflects the saved entity (including id).
        assertThat(result).isEqualTo(savedDto);
        verify(productRepository).save(entity);
    }
}
