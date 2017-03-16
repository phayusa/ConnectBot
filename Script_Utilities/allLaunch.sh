SUDO=''
if (( $EUID != 0 )); then
    SUDO='sudo'
fi

$SUDO python ../manage.py runserver $1 &
$SUDO python script.py ip=$1 &
$SUDO python read.py ip=$1 &
