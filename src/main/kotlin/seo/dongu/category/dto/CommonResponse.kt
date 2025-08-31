package seo.dongu.category.dto

data class CommonResponse<T>(
  val success: Boolean,
  val data: T? = null,
  val error: ErrorDetail? = null
) {
  companion object {
    fun <T> success(data: T): CommonResponse<T> {
      return CommonResponse(
        success = true,
        data = data
      )
    }

    fun <T> error(code: String, message: String): CommonResponse<T> {
      return CommonResponse(
        success = false,
        error = ErrorDetail(code, message)
      )
    }
  }
}

data class ErrorDetail(
  val code: String,
  val message: String,
  val details: Map<String, Any>? = null
)