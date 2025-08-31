package seo.dongu.category.controller

import org.springframework.web.bind.annotation.*
import seo.dongu.category.dto.CategoryTreeDto
import seo.dongu.category.service.CategoryCacheService
import seo.dongu.category.service.CategoryCreateService
import seo.dongu.category.service.CategoryDeleteService
import seo.dongu.category.service.CategoryMoveService

@RestController
@RequestMapping("/api/categories")
class CategoryController(
  private val categoryCacheService: CategoryCacheService,
  private val categoryCreateService: CategoryCreateService,
  private val categoryDeleteService: CategoryDeleteService,
  private val categoryMoveService: CategoryMoveService
) {

  @GetMapping
  fun getAllCategories(): List<CategoryTreeDto> {
    return categoryCacheService.getAllCategories()
  }

  @GetMapping("/{id}")
  fun getCategory(@PathVariable id: Long): CategoryTreeDto {
    return categoryCacheService.getCategory(id)
  }

  @PostMapping
  fun createCategory(
    @RequestParam name: String,
    @RequestParam(required = false) parentId: Long?
  ): CategoryTreeDto {
    return categoryCreateService.createCategory(name, parentId)
  }

  @DeleteMapping("/{id}")
  fun deleteCategory(
    @PathVariable id: Long
  ): Boolean {
    categoryDeleteService.deleteCategory(id)
    return true
  }

  @PutMapping("/{id}/move")
  fun moveCategory(
    @PathVariable id: Long,
    @RequestParam(required = false) newParentId: Long?
  ): Boolean {
    categoryMoveService.moveCategory(id, newParentId)
    return true
  }
}