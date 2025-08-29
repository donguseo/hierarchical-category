package seo.dongu.category.controller

import org.springframework.web.bind.annotation.*
import seo.dongu.category.dto.CategoryTreeDto
import seo.dongu.category.service.CategoryCommandService
import seo.dongu.category.service.CategoryMoveService
import seo.dongu.category.service.CategoryQueryService

@RestController
@RequestMapping("/api/categories")
class CategoryController(
  private val categoryQueryService: CategoryQueryService,
  private val categoryCommandService: CategoryCommandService,
  private val categoryMoveService: CategoryMoveService
) {

  @GetMapping
  fun getAllCategories(): List<CategoryTreeDto> {
    return categoryQueryService.getAllCategories()
  }

  @GetMapping("/{id}")
  fun getCategory(@PathVariable id: Long): CategoryTreeDto {
    return categoryQueryService.getCategory(id)
  }

  @PostMapping
  fun createCategory(
    @RequestParam name: String,
    @RequestParam(required = false) parentId: Long?
  ): CategoryTreeDto {
    return categoryCommandService.createCategory(name, parentId)
  }

  @DeleteMapping("/{id}")
  fun deleteCategory(
    @PathVariable id: Long
  ) {
    categoryCommandService.deleteCategory(id)
  }

  @PutMapping("/{id}/move")
  fun moveCategory(
    @PathVariable id: Long,
    @RequestParam(required = false) newParentId: Long?
  ) {
    categoryMoveService.moveCategory(id, newParentId)
  }
}