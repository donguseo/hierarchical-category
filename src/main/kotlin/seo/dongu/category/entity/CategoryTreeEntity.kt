package seo.dongu.category.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(
  name = "category_tree",
  indexes = [
    // ancdestor_id는 primary key 생성 시 자동 multi 인덱스 생성되어 decendant_id만 단일 인덱스 생성
    Index(name = "idx_descendant_id", columnList = "descendant_id")
  ]
)
@IdClass(CategoryTreeId::class)
class CategoryTreeEntity(
  @Id
  @Column(name = "ancestor_id")
  val ancestorId: Long,
  @Id
  @Column(name = "descendant_id")
  val descendantId: Long,
  @Column(name = "depth")
  val depth: Int
) {
  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Column(name = "created_at")
  lateinit var createdAt: LocalDateTime
}