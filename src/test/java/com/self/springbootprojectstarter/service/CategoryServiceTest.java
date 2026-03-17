package com.self.springbootprojectstarter.service;

import com.self.springbootprojectstarter.DTO.CategoryDto;
import com.self.springbootprojectstarter.model.Category;
import com.self.springbootprojectstarter.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void addCategory_returnsSavedDto() {
        CategoryDto dto = new CategoryDto("books");
        Category entity = new Category();
        Category saved = new Category();
        saved.setId(1L);
        saved.setName("books");
        CategoryDto savedDto = new CategoryDto("books");
        savedDto.setId(1L);

        when(modelMapper.map(dto, Category.class)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(saved);
        when(modelMapper.map(saved, CategoryDto.class)).thenReturn(savedDto);

        CategoryDto result = categoryService.addCategory(dto);

        assertThat(result).isEqualTo(savedDto);
        verify(categoryRepository).save(entity);
    }
}
