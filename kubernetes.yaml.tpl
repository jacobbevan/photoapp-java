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
      volumes:
      - name: google-cloud-key
        secret:
          secretName: store-key
      containers:
      - name: photoapp
        image: gcr.io/GOOGLE_CLOUD_PROJECT/photoapp:COMMIT_SHA
        ports:
        - containerPort: 8080
        volumeMounts:
        - name: google-cloud-key
          mountPath: /var/secrets/google
        env:
        - name: GOOGLE_APPLICATION_CREDENTIALS
          value: /var/secrets/google/key.json
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