Restart your stack: docker-compose down && docker-compose up -d

Open SonarQube: Go to http://localhost:9000 (User: admin / Pass: admin).

Generate a Token: Go to My Account > Security and generate a "Global Analysis Token".

Paste the token into the scan-all.sh script above.

Run the scan:

Bash
chmod +x scan-all.sh
./scan-all.sh
Why this fits your project:
Persistent: Because we added sonarqube_data to your volumes, your analysis history will stay even if you stop the containers.

Isolated: It maps your local subdirectories (/backend/identity, etc.) into the scanner container temporarily, keeping your host machine clean.

Microservice Ready: By giving each folder a unique sonar.projectKey (like hb-identity), you get a separate dashboard for every service in the UI.