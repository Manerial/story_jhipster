last_updated_at=""
process_id=""
java_win="/c/Program Files/Java/jdk-11.0.2/bin/java"

get_artifact() {
	echo "Start get artifact"
	artifact_infos=$(curl -H "Accept: application/vnd.github.v3+json" https://api.github.com/repos/Manerial/story_jhipster/actions/artifacts)
	
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

	until $(curl -H "Accept: application/vnd.github.v3+json" --netrc-file C:\Users\Steven\_netrc -L -o $file_name $1 -u Manerial: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); do
		sleep 5
	done

	unzip "$file_name"
	rm "$file_name"
	"$java_win" -jar nid-aux-histoires-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod,swagger &
	process_id=$!
}

start_loop() {
	get_artifact
	sleep 30m
	start_loop
}

start_loop