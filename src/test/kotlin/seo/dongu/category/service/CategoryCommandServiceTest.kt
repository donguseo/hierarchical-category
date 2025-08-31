package seo.dongu.category.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class CategoryCommandServiceTest {

  @Autowired
  lateinit var categoryCommandService: CategoryCommandService

  @Autowired
  lateinit var categoryQueryService: CategoryQueryService


  @Test
  fun `Category 생성`() {
    // given
    val createdCategory = categoryCommandService.createCategory("Root", null)

    // when
    val category = categoryQueryService.getCategory(createdCategory.id)

    // then
    assertEquals(createdCategory.id, category.id)
    assertEquals("Root", category.name)
    assertEquals(null, category.parentId)
    assertEquals(0, category.children.size)
  }

  @Test
  fun `Category 생성 depth 1`() {
    // given
    val createdCategory = categoryCommandService.createCategory("Root", null)
    categoryCommandService.createCategory("child1", createdCategory.id)
    categoryCommandService.createCategory("child2", createdCategory.id)
    categoryCommandService.createCategory("child3", createdCategory.id)

    // when
    val category = categoryQueryService.getCategory(createdCategory.id)

    // then
    assertEquals(createdCategory.id, category.id)
    assertEquals("Root", category.name)
    assertEquals(null, category.parentId)
    assertEquals(3, category.children.size)
  }


  @Test
  fun `Category 생성 depth 2`() {
    // given
    val createdCategory = categoryCommandService.createCategory("Root", null)
    categoryCommandService.createCategory("child1", createdCategory.id)
    categoryCommandService.createCategory("child2", createdCategory.id)
    val childCategory = categoryCommandService.createCategory("child3", createdCategory.id)
    categoryCommandService.createCategory("grandchild1", childCategory.id)
    categoryCommandService.createCategory("grandchild2", childCategory.id)

    // when
    val category = categoryQueryService.getCategory(createdCategory.id)

    // then
    assertEquals(createdCategory.id, category.id)
    assertEquals("Root", category.name)
    assertEquals(null, category.parentId)
    assertEquals(3, category.children.size)

    category.children[2].let { child ->
      assertEquals(childCategory.id, child.id)
      assertEquals("child3", child.name)
      assertEquals(2, child.children.size)
    }
  }
}