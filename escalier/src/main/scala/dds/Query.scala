package dds

/**
 * The <code>Query</code> class reprents a generic DDS query or filter expression,
 * such as those used with content filtering or read-queries.
 */

case class Query(val expression: String, val params: List[String])
