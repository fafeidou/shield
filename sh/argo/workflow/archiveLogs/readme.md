
kubectl get workflows -n argo

kubectl get workflow hostpath-demo-mhds4-pnd4w -o json -n argo | jq -r '.status.nodes[] | select(.outputs.artifacts != null) | .outputs.artifacts[0].s3.key'


