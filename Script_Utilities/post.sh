if [ $# -lt 3 ]
  then
    echo "Usage $0 Path Attribute_To_Fill"
    exit 1
fi
http --form POST http://localhost:8000/Api/$1 $2
