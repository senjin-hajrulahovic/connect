# Connect.measure

## About
This is a simple tool for network performance measurement

## Usage

run<br> `mvn package`<br>
then run<br>
`java -jar target/connect.me-1.0-SNAPSHOT-jar-with-dependencies.jar`<br>
with following arguments for catcher<br>
`-c -bind localhost -port 9092`<br>
and following for pitcher<br>
`-p -hostname localhost -port 9092 -mps 10 -size 100`

## Arguments<br>
`-c` to run in catcher mode<br>
`-p` to run in pitcher mode<br>
`-bind` to define bind address for catcher<br>
`-hostname` to define catcher hostname<br>
`-port` to define port for pitcher/chatcher<br>
`-mps` for number of sent messages per second<br>
`-size` for the size of every individual message<br>