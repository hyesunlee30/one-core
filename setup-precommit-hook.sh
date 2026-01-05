#!/bin/bash
set -euo pipefail

# Pre-commit hook 경로
HOOK_PATH=".git/hooks/pre-commit"

# Pre-commit hook 내용
HOOK_CONTENT=$(cat << 'EOF'
#!/bin/sh
set -euo pipefail

# gradlew 권한 부여
chmod +x ./gradlew

# ktlint 수행
./gradlew formatKotlin

# 만약 포맷팅 후 변경된 내용이 없으면 커밋을 중단
if git diff --name-only --cached --quiet; then
  echo "🔮 포맷팅 후 변경점이 없으므로 커밋을 중단합니다."
  exit 1
fi

# 테스트 실행
echo "🔎 테스트를 수행합니다."
if ! ./gradlew test; then
  echo "🔴 테스트코드 통과에 실패하였으므로 커밋을 중단합니다."
  exit 1
fi

# 커밋 성공 메시지
echo "🎉 커밋이 정상적으로 완료되었습니다."
EOF
)

# Pre-commit hook 파일 생성
echo "📝 pre-commit hook 내용을 생성합니다..."
echo "$HOOK_CONTENT" > "$HOOK_PATH"

echo "🔑 Pre-commit hook 에 실행 권한을 부여합니다..."
chmod +x "$HOOK_PATH"

# 확인 메시지 출력
if [ -f "$HOOK_PATH" ]; then
    echo "  ✅  $HOOK_PATH 에 설정 완료"
else
    echo "  ❌ pre-commit hook 설정 실패"
fi

# 권한 부여 확인
if [ -x "$HOOK_PATH" ]; then
    echo "  ✅  실행 권한 부여 완료"
else
    echo "  ❌ 실행 권한 부여 실패"
fi

echo "🎉 pre-commit hook 설정이 완료되었습니다."