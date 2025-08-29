package seo.dongu.category.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import seo.dongu.category.entity.CategoryEntity
import seo.dongu.category.entity.CategoryTreeEntity

interface CategoryTreeRepository : JpaRepository<CategoryTreeEntity, Long> {
  fun findByDescendantId(descendantId: Long): List<CategoryTreeEntity>
  fun findByAncestorId(ancestorId: Long): List<CategoryTreeEntity>

  @Query(
    """
    SELECT DISTINCT c
    FROM CategoryTreeEntity ct
    JOIN CategoryEntity c ON c.id = ct.descendantId
    WHERE ct.ancestorId = :ancestorId
  """
  )
  fun findCategoriesByAncestorId(@Param("ancestorId") ancestorId: Long): List<CategoryEntity>
}