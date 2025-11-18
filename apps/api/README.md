# API File Upload Configuration

This document outlines the configuration for the file upload functionality in the Ktor API.

## Overview

The system supports two providers for file storage: `local` for development and `s3` for production. The choice of provider is determined by the `UPLOAD_PROVIDER` environment variable.

## Environment Variables

To configure the upload system, you need to set the following environment variables in your `.env` file. You can use the `.env.example` file as a template.

### General Configuration

- `UPLOAD_PROVIDER`: The storage provider to use.
  - `local`: (Default) Stores files on the local server filesystem.
  - `s3`: Stores files in an Amazon S3 bucket.

### Local Storage Configuration

These variables are used when `UPLOAD_PROVIDER` is set to `local`.

- `LOCAL_UPLOAD_PATH`: The local directory path where files will be stored.
  - **Default**: `assets/uploads`
- `LOCAL_UPLOAD_BASE_URL`: The public base URL from which local files will be served.
  - **Default**: `http://localhost:8080/uploads`

**Example `.env` for Local Storage:**
```
UPLOAD_PROVIDER=local
LOCAL_UPLOAD_PATH=assets/uploads
LOCAL_UPLOAD_BASE_URL=http://localhost:8080/uploads
```

### Amazon S3 Configuration

These variables are used when `UPLOAD_PROVIDER` is set to `s3`.

- `AWS_S3_BUCKET`: The name of your S3 bucket.
- `AWS_S3_REGION`: The AWS region where your bucket is located (e.g., `us-east-1`).
- `AWS_S3_ACCESS_KEY`: Your AWS access key ID.
- `AWS_S3_SECRET_KEY`: Your AWS secret access key.
- `AWS_CLOUDFRONT_DOMAIN`: (Optional) The domain of your CloudFront distribution (e.g., `https://d123abcdef.cloudfront.net`). If provided, this will be used to construct the public URL for uploaded files. If omitted, the standard S3 URL will be used.

**Example `.env` for S3 Storage:**
```
UPLOAD_PROVIDER=s3
AWS_S3_BUCKET=your-travel-bucket-name
AWS_S3_REGION=ap-southeast-1
AWS_S3_ACCESS_KEY=YOUR_AWS_ACCESS_KEY
AWS_S3_SECRET_KEY=YOUR_AWS_SECRET_KEY
AWS_CLOUDFRONT_DOMAIN=https://cdn.yourdomain.com
```

## API Endpoint

- `POST /api/uploads`
  - **Request**: `multipart/form-data` with a single file part.
  - **Constraints**:
    - Allowed file types: `jpg`, `jpeg`, `png`, `webp`
    - Maximum file size: 5MB
  - **Success Response (200 OK)**:
    ```json
    {
      "success": true,
      "data": {
        "url": "https://your-cdn.com/uploads/2025/01/uuid.webp",
        "fileName": "uuid.webp"
      },
      "message": "Success"
    }
    ```
  - **Error Responses**:
    - `400 Bad Request`: If the file format is invalid or no file is provided.
    - `413 Payload Too Large`: If the file size exceeds 5MB.
```
