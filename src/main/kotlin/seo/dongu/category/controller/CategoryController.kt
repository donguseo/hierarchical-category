package seo.dongu.category.controller

import org.springframework.web.bind.annotation.*
import seo.dongu.category.dto.CategoryTreeDto
import seo.dongu.category.dto.CommonResponse
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
  fun getAllCategories(): CommonResponse<List<CategoryTreeDto>> {
    return CommonResponse.success(categoryCacheService.getAllCategories())
  }

  @GetMapping("/{id}")
  fun getCategory(@PathVariable id: Long): CommonResponse<CategoryTreeDto> {
    return CommonResponse.success(categoryCacheService.getCategory(id))
  }

  @PostMapping
  fun createCategory(
    @RequestParam name: String,
    @RequestParam(required = false) parentId: Long?
  ): CommonResponse<CategoryTreeDto> {
    return CommonResponse.success(categoryCreateService.createCategory(name, parentId))
  }

  @DeleteMapping("/{id}")
  fun deleteCategory(
    @PathVariable id: Long
  ): CommonResponse<Boolean> {
    categoryDeleteService.deleteCategory(id)
    return CommonResponse.success(true)
  }

  @PutMapping("/{id}/move")
  fun moveCategory(
    @PathVariable id: Long,
    @RequestParam(required = false) newParentId: Long?
  ): CommonResponse<Boolean> {
    categoryMoveService.moveCategory(id, newParentId)
    return CommonResponse.success(true)
  }
}