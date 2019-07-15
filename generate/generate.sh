#!/bin/bash
population=150
states=( "Alabama" "Alaska" "Arizona" "Arkansas" "California" "Colorado" "Connecticut" "Delaware" "Florida" "Georgia" "Hawaii" "Idaho" "Illinois" "Indiana" "Iowa" "Kansas" "Kentucky" "Louisiana" "Maine" "Maryland" "Massachusetts" "Michigan" "Minnesota" "Mississippi" "Missouri" "Montana" "Nebraska" "Nevada" "New Hampshire" "New Jersey" "New Mexico" "New York" "North Carolina" "North Dakota" "Ohio" "Oklahoma" "Oregon" "Pennsylvania" "Rhode Island" "South Carolina" "South Dakota" "Tennessee" "Texas" "Utah" "Vermont" "Virginia" "Washington" "West Virginia" "Wisconsin" "Wyoming" )
url=""
max=50
while getopts p:u: option
	do
		case "${option}"
		in
			p) population=${OPTARG};;
			u) url=${OPTARG};;
		esac
done
[ -z "$url" ] && echo "Missing required -u flag for API URL"  && exit 1
git clone https://github.com/synthetichealth/synthea.git
cd synthea || exit 1
sed -e 's/^\(exporter.years_of_history =\).*/\1 0/' -e 's/^\(exporter.csv.export =\).*/\1 true/' src/main/resources/synthea.properties > src/main/resources/synthea.properties.new
mv src/main/resources/synthea.properties.new src/main/resources/synthea.properties
seed=1
for ((n=0;n<$population;n=$n+$max))
do
	populationChunk=$(($population - $n))
	state=$(($RANDOM % 49))
	currentState=${states[$state]}
	if test $populationChunk -lt $max; then
		./run_synthea -s $seed -p "$populationChunk" "$currentState"
  	else
  		populationChunk=$max
  		./run_synthea -s $seed -p "$populationChunk" "$currentState"
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
	statusCode=$(curl --write-out %{http_code} --silent --output /dev/null "$url/resources/v1/generate" -H "Content-Type: application/json" -X POST -d "@apidata.json")
	rm -rf apidata.json
	if (($statusCode >= 400)); then
		rm -rf synthea
		echo "Unable to finish generating $population patients. Error from $url/resources/v1/generate"
		echo "HTTP status code: $statusCode"
		exit 1
	fi
	cd synthea
	rm -rf output
done
cd ..
rm -rf synthea
