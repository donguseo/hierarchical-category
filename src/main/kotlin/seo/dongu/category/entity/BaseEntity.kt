package seo.dongu.category.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  var id: Long? = null

  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Column(name = "created_at")
  lateinit var createdAt: LocalDateTime

  @LastModifiedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Column(name = "updated_at")
  lateinit var updatedAt: LocalDateTime

}
