package seo.dongu.category.dto

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import seo.dongu.category.entity.CategoryEntity

class CategoryTreeDtoMakerTest {

  @Test
  fun `단일 카테고리 트리 생성`() {
    // given
    val rootCategory = createCategory(1L, "Root", null)
    val categories = listOf(rootCategory)

    // when
    val result = buildCategoryTree(1L, categories)

    // then
    assertEquals(1L, result.id)
    assertEquals("Root", result.name)
    assertNull(result.parentId)
    assertTrue(result.children.isEmpty())
  }

  @Test
  fun `depth 1 카테고리 트리 생성`() {
    // given
    val rootCategory = createCategory(1L, "Root", null)
    val child1 = createCategory(2L, "Child1", 1L)
    val child2 = createCategory(3L, "Child2", 1L)
    val child3 = createCategory(4L, "Child3", 1L)

    val categories = listOf(rootCategory, child1, child2, child3)

    // when
    val result = buildCategoryTree(1L, categories)

    // then
    assertEquals(1L, result.id)
    assertEquals("Root", result.name)
    assertNull(result.parentId)
    assertEquals(3, result.children.size)

    // 자식 노드 검증
    val childrenIds = result.children.map { it.id }
    assertEquals(listOf(2L, 3L, 4L), childrenIds)

    val childrenNames = result.children.map { it.name }
    assertEquals(listOf("Child1", "Child2", "Child3"), childrenNames)

    // 모든 자식의 parentId가 1인지 확인
    result.children.forEach { child ->
      assertEquals(1L, child.parentId)
      assertTrue(child.children.isEmpty())
    }
  }

  @Test
  fun `중간 노드부터 시작하는 트리 생성`() {
    // given
    val rootCategory = createCategory(1L, "Root", null)
    val child1 = createCategory(2L, "Child1", 1L)
    val grandChild1 = createCategory(3L, "GrandChild1", 2L)
    val grandChild2 = createCategory(4L, "GrandChild2", 2L)

    val categories = listOf(rootCategory, child1, grandChild1, grandChild2)

    // when - Child1부터 시작
    val result = buildCategoryTree(2L, categories)

    // then
    assertEquals(2L, result.id)
    assertEquals("Child1", result.name)
    assertEquals(1L, result.parentId)
    assertEquals(2, result.children.size)

    val childrenIds = result.children.map { it.id }
    assertEquals(listOf(3L, 4L), childrenIds)
  }

  private fun createCategory(id: Long, name: String, parentId: Long?): CategoryEntity {
    return CategoryEntity(name, parentId).apply {
      this.id = id
    }
  }


  @Test
  fun `depth 2 카테고리 트리 생성`() {
    // given
    val rootCategory = createCategory(1L, "Root", null)
    val child1 = createCategory(2L, "Child1", 1L)
    val child2 = createCategory(3L, "Child2", 1L)
    val child3 = createCategory(4L, "Child3", 1L)
    val child4 = createCategory(5L, "Child3", 2L)

    val categories = listOf(rootCategory, child1, child2, child3, child4)

    // when
    val result = buildCategoryTree(1L, categories)

    // then
    assertEquals(1L, result.id)
    assertEquals("Root", result.name)
    assertNull(result.parentId)
    assertEquals(3, result.children.size)

    // 자식 노드 검증
    val childrenIds = result.children.map { it.id }
    assertEquals(listOf(2L, 3L, 4L), childrenIds)

    result.children[0].let { child ->
      assertEquals(2L, child.id)
      assertEquals("Child1", child.name)
      assertEquals(1L, child.parentId)
      assertEquals(1, child.children.size)
      assertEquals(5L, child.children[0].id)
    }
  }
}



