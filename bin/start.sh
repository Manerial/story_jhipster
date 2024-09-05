last_updated_at=""
process_id=""

get_artifact() {
	echo "Start get artifact"
	artifact_infos=$(curl https://api.github.com/repos/Manerial/story_jhipster/actions/artifacts?per_page=1)
	
	updated_at=$(get_artifact_info "$artifact_infos" "updated_at")
	archive_download_url=$(get_artifact_info "$artifact_infos" "archive_download_url")
	
	if [[ "$last_updated_at" != "$updated_at" ]]; then
		last_updated_at="$updated_at"
		update_server "$archive_download_url"
	fi
	
	echo "End get artifact"
}

get_artifact_info() {
	artifact_info=$(echo "$1" | grep $2)
	regex="\"$2\": \"(.*)\""
	[[ $artifact_info =~ $regex ]]
	echo "${BASH_REMATCH[1]}"
}

update_server() {
	if [[ "$process_id" != "" ]]; then
		kill $process_id
	fi
	
	file_name="website_jar.zip"
	token=$(printenv GITHUB_TOKEN)
	
	echo "$token"
	echo "$file_name"
	echo "$1"

	until $(curl -H "Accept: application/vnd.github.v3+json" $1 -L -o $file_name -u Manerial:$token); do
		sleep 5
	done

	unzip "$file_name"
	rm "$file_name"
	
	java -jar nid-aux-histoires-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod,swagger &
	process_id=$!
}

start_loop() {
	get_artifact
	sleep 30m
	start_loop
}

start_loop