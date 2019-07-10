#!/bin/bash
population=500
state=California
url="http://localhost:3000"
max=500
while getopts p:s:u: option
	do
		case "${option}"
		in
			p) population=${OPTARG};;
			s) state=${OPTARG};;
			u) url=${OPTARG};;
		esac
done
git clone https://github.com/synthetichealth/synthea.git
cd synthea || exit 1
sed -e 's/^\(exporter.years_of_history =\).*/\1 0/' -e 's/^\(exporter.csv.export =\).*/\1 true/' src/main/resources/synthea.properties > src/main/resources/synthea.properties.new
mv src/main/resources/synthea.properties.new src/main/resources/synthea.properties
seed=1
for ((n=0;n<$population;n=$n+$max))
do
	populationChunk=$(($population - $n))
	if test $populationChunk -lt $max; then
		./run_synthea -s $seed -p "$populationChunk" "$state"
  	else
  		populationChunk=$max
  		./run_synthea -s $seed -p "$populationChunk" "$state"
  	fi
  	seed=$(($seed + 1))
  	mv output/csv/allergies.csv ../allergies.csv
	mv output/csv/patients.csv ../patients.csv
	mv output/csv/observations.csv ../observations.csv
	mv output/csv/medications.csv ../medications.csv
	mv output/csv/encounters.csv ../encounters.csv
	mv output/csv/providers.csv ../providers.csv
	mv output/csv/organizations.csv ../organizations.csv
	cd ..
	csvtojson allergies.csv > allergies.json
	csvtojson patients.csv > patients.json
	csvtojson observations.csv > observations.json
	csvtojson medications.csv > medications.json
	csvtojson encounters.csv > encounters.json
	csvtojson providers.csv > providers.json
	csvtojson organizations.csv > organizations.json
	sed -e '1s/^/{"allergies":/' allergies.json > apidata.json
	{
	    echo ',"patients":'
	    cat patients.json
	    echo ',"observations":'
	    cat observations.json
	    echo ',"medications":'
	    cat medications.json
	    echo ',"encounters":'
	    cat encounters.json
	    echo ',"providers":'
	    cat providers.json
	    echo ',"organizations":'
	    cat organizations.json
	    echo "}"
	} >> apidata.json
	rm -rf allergies.csv
	rm -rf allergies.json
	rm -rf patients.csv
	rm -rf patients.json
	rm -rf observations.csv
	rm -rf observations.json
	rm -rf medications.csv
	rm -rf medications.json
	rm -rf encounters.csv
	rm -rf encounters.json
	rm -rf providers.csv
	rm -rf providers.json
	rm -rf organizations.csv
	rm -rf organizations.json
	cd synthea
	rm -rf output
done
cd ..
rm -rf synthea
