# Sixth-Sense 비식별 프로그램 Frontend
- 개발자
    - 노설
        + 20200604 
            + 업로드 파일 관련 file_info mybatis설정 및 DB 추가, 설정
            + 비식별 처리 관련 result_log mybatis설정 및 DB 추가, 설정
        + 20200606
            + 파일 다운로드 관련 API 처리 
            + 추후 파일 리스트와 result_log 테이블과 매핑시킬지의 여부 팀과 상의 예정
        + 20200609
            + 처리로그 관련 API
        + 20200611
            + 갑자기 발생한 회원가입 API 오류 해결 (git clone 했을 경우 회원가입이 안되는 현상)
            > 입력받은 객체를 받아 ModelAndView에 직접 넣어주었다.
    - 이동욱
        + 20200605
            + 파일리스트 출력 반복문 기초 설정
        + 20200607
            + index 필요없는 부분 일부 삭제
            + 파일리스트 상세정보 페이지 생성 및 데이터 이동 확인
            + 메인 background 설정 및 summarydetail 
        + 20200608
            + mypage에 파일리스트 페이지 생성
            + username에 세션 유지한 상태로 가져옴  
        + 20200611
            + ui 최종 전단계 수정
    - 최민영
        + 20200605
            + MongoDB 셋팅
        + 20200610
            + 사용자 선택 DB 업데이트
            + 비식별화 서비스와 연동K
    
apiVersion: apps/v1
kind: Deployment
metadata:
  name: login-app-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      app: login-app
  template:
    metadata:
      labels:
        app: login-app
    spec:
      containers:
      - name: login-app
        image: wlgns0719/login-app-service:latest
        ports:
        - containerPort: 8080
        imagePullPolicy: Always
------
apiVersion: v1
kind: Service
metadata:
  name: login-app-service
spec:
  ports:
    - name: "8080"
      port: 8082
      targetPort: 8087
  selector:
    app: login-app
  type: NodePort
