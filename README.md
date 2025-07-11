📄 PDF Merger & Splitter Backend
This is a backend application built using Spring Boot, MongoDB, and WebSocket that allows users to:

✅ Merge multiple PDF files

✅ Split a PDF file at a specified page

✅ Track operation status via MongoDB

✅ (Optional) Notify real-time status via WebSocket

✅ Download the processed PDF

📁 Folder Structure
graphql
Copy
Edit
com.pdfmanager.pdfbackend
├── controller       # REST + WebSocket controllers
├── service          # PDF merging/splitting logic
├── model            # MongoDB document
├── repository       # MongoDB interface
├── dto              # Data transfer objects
├── config           # WebSocket and MongoDB configuration
⚙️ Tech Stack
Java 17

Spring Boot

MongoDB

WebSocket (STOMP)

Apache PDFBox

Maven

🚀 Running the Project
MongoDB must be running locally (default port 27017)

Clone and run the project:

bash
Copy
Edit
git clone https://github.com/YOUR_USERNAME/pdf-merger-splitter-backend.git
cd pdf-merger-splitter-backend
mvn spring-boot:run
Make sure the following folder exists:

bash
Copy
Edit
mkdir pdf-files
Or it will be auto-created inside the working directory.

🔐 Endpoints
📨 Merge PDFs
bash
Copy
Edit
POST /api/pdf/merge
form-data key: files → (multi-part file upload)

✂️ Split PDF
bash
Copy
Edit
POST /api/pdf/split
form-data:

file → PDF file

splitAfterPage → e.g., 2

📥 Download
swift
Copy
Edit
GET /api/pdf/download/{fileName}
📦 Example Response
json
Copy
Edit
{
"message": "Operation successful",
"downloadUrl": "/api/pdf/download/merged_abc123.pdf"
}
📡 WebSocket (Optional)
Endpoint: /ws

Topic to subscribe: /topic/task-status

Send to: /app/notify (if needed)

🗃 MongoDB Document Example
json
Copy
Edit
{
"_id": "66aa4f...",
"type": "MERGE",
"timestamp": "2025-07-06T12:00:00",
"status": "SUCCESS",
"resultUrl": "/api/pdf/download/merged_abc.pdf"
}
✅ To Do
Add Swagger UI

Add user authentication (optional)

Deploy on cloud (e.g. Render, AWS)

Upload to S3 or DB instead of local disk

👨‍💻 Author
Built by Your Name