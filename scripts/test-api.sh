 #!/bin/sh
 # Usage: ./scripts/test-api.sh [http://localhost:8080]
 set -eu
 BASE="${1:-http://localhost:8080}"
 echo '1) Create book'
 curl -i -X POST "$BASE/api/books" -H 'Content-Type: application/json' \
   --data-binary @examples/book.json
 echo '
2) List books'
 curl -i "$BASE/api/books"
 echo '
3) To update or delete, use the actual id returned by POST, for example:'
 echo "curl -i -X PUT $BASE/api/books/1 -H 'Content-Type: application/json' --data-binary @examples/book-update.json"
 echo "curl -i -X DELETE $BASE/api/books/1"
