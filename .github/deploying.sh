source credentials.sh
docker pull dokebi-shop.kr-gov-central-1.kcr.dev/dokebi-repo/dokebi-was:blue
docker stop spring_blue 2>/dev/null || true
infisical export --path="/DB"  --format=dotenv > .env
python string_to_num.py
docker run --name spring_blue --rm -itd -p 8080:$1112 --env-file .env dokebi-shop.kr-gov-central-1.kcr.dev/dokebi-repo/dokebi-was:blue