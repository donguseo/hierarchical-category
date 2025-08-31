package seo.dongu.category.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class CategoryMoveServiceTest {

  @Autowired
  lateinit var categoryCommandService: CategoryCommandService

  @Autowired
  lateinit var categoryMoveService: CategoryMoveService

  @Autowired
  lateinit var categoryQueryService: CategoryQueryService


  @Test
  fun `Category 이동 - leaf to root`() {
    // given
    val createdCategory = categoryCommandService.createCategory("Root", null)
    categoryCommandService.createCategory("child1", createdCategory.id)
    categoryCommandService.createCategory("child2", createdCategory.id)
    val childCategory = categoryCommandService.createCategory("child3", createdCategory.id)
    categoryCommandService.createCategory("grandchild1", childCategory.id)
    val leaf = categoryCommandService.createCategory("grandchild2", childCategory.id)

    // when
    categoryMoveService.moveCategory(leaf.id, null)
    val category = categoryQueryService.getCategory(leaf.id)

    // then
    assertEquals(leaf.id, category.id)
    assertEquals("grandchild2", category.name)
    assertEquals(null, category.parentId)
    assertEquals(0, category.children.size)
  }

  @Test
  fun `Category 이동 - 중간 node to root`() {
    // given
    val createdCategory = categoryCommandService.createCategory("Root", null)
    categoryCommandService.createCategory("child1", createdCategory.id)
    categoryCommandService.createCategory("child2", createdCategory.id)
    val childCategory = categoryCommandService.createCategory("child3", createdCategory.id)
    categoryCommandService.createCategory("grandchild1", childCategory.id)
    categoryCommandService.createCategory("grandchild2", childCategory.id)

    // when
    categoryMoveService.moveCategory(childCategory.id, null)
    val category = categoryQueryService.getCategory(childCategory.id)

    // then
    assertEquals(childCategory.id, category.id)
    assertEquals("child3", category.name)
    assertEquals(null, category.parentId)
    assertEquals(2, category.children.size)
  }

  @Test
  fun `Category 이동 - 중간 node to 다른 node 하위로`() {
    // given
    val createdCategory = categoryCommandService.createCategory("Root", null)
    categoryCommandService.createCategory("child1", createdCategory.id)
    val target = categoryCommandService.createCategory("child2", createdCategory.id)
    val childCategory = categoryCommandService.createCategory("child3", createdCategory.id)
    categoryCommandService.createCategory("grandchild1", childCategory.id)
    categoryCommandService.createCategory("grandchild2", childCategory.id)

    // when
    categoryMoveService.moveCategory(childCategory.id, target.id)
    val category = categoryQueryService.getCategory(target.id)

    // then
    assertEquals(target.id, category.id)
    assertEquals("child2", category.name)
    assertEquals(1, category.children.size)

    category.children[0].let {
      assertEquals(childCategory.id, it.id)
      assertEquals("child3", it.name)
      assertEquals(2, it.children.size)
    }
  }


}