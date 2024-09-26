#!/usr/bin/env bash

# Wait-for script to check if a service is available
set -e

host="$1"
shift
cmd="$@"

until nc -z "$host" 8889; do
  >&2 echo "Waiting for $host:8889 to be available..."
  sleep 1
done

>&2 echo "$host:8889 is available!"

exec $cmd
