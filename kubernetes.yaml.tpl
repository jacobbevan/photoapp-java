apiVersion: apps/v1
kind: Deployment
metadata:
  name: photoapp
  labels:
    app: photoapp
spec:
  replicas: 1
  selector:
    matchLabels:
      app: photoapp
  template:
    metadata:
      labels:
        app: photoapp
    spec:
      containers:
      - name: photoapp
        image: gcr.io/GOOGLE_CLOUD_PROJECT/photoapp:COMMIT_SHA
        ports:
        - containerPort: 8080
---
kind: Service
apiVersion: v1
metadata:
  name: photoapp
spec:
  selector:
    app: photoapp
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
type: LoadBalancer