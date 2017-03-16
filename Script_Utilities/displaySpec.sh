if [ $# -eq 0 ]
  then
    echo "$0 ([1-9]+)"
    exit 1
fi
http http://localhost:8000/Api/message/	
http http://localhost:8000/Api/message/$1.json
