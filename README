# OAuth

OAuth란? 
토큰 기반의 인증 및 권한을 위한 표준 프로토콜.
OAuth와 같은 인증 프로토콜을 통해 유저의 정보를 페이스북, 구글, 카카오 등의 서비스에서 제공받을 수 있고
이 정보를 기반으로 어플리케이션 사용자에게 로그인이나 다른 여러 기능들을 손쉽게 제공할 수 있다.

	개발자는 Facebook등과 같은 서비스에게 accessToken을 발급 받음(id 비밀번호 대신에 받음)
	accessToken을 활용해 소셜 서비스에 접근해 데이터를 가져오고 수정 조회 등의 작업을 함
  
mine : Client 내가 만든 서비스

User : Resource Owner Their라는 서비스에 회원가입이 되어있는 상태

Their : Resource Server(Facebook등)

1. 등록
 	Client가 Resource Server의 서비스를 이용하기 위해 사전에 등록을 해야함.
	보통 Client ID, Client Secret, Authorized redirect URIs를 받음
				Resource Server가 Authorized code를 전달해줄 때 해당 주소로 전달함

2. Resource Owner의 승인
	Resource Owner가 어플리케이션을 이용할 때 Resource Server를 사용할 때가 있다.(ex 페북에 글을 게시) 그럴 때 Client가 Resource Owner에게 facebook으로 로그인하기 등 버튼이 있는 화면을 띄움

	버튼에는 링크가 있는데, https://~/?clientid=~&scope=B,C&redirect_uri=~ 이런 형식의 링크를 담고 있음.
	클라이언트 ID, 사용하고자하는 기능, redirect주소 3가지
	위 주소로 Resource Owner가 Resource Server로 접속을 하게 되면 Server가 Owner가 로그인 되어있는지 아닌지 판단
	만약 안 되어 있으면 로그인 하라는 창 줌
	
	로그인 성공 시 clientid와 같은 값이 있는지 Resource Server가 판단
	그리고 같은 아이디면 redirect_uri 값을 비교해서 다르면 작업 중단 
	같다면 Resource Owner에게 Scope에 해당되는 권한을 Client에게 부여할 것인지를 확인하는 메세지를 보냄
	허용한다면 Resource Server는 user id:1(Owner는 user id 1이라 가정)은 Scope B,C를 사용하는데 동의하였다라는 내용을 저장

3. Resource Server의 승인
	Authorization code를 Resource Server가 Resource Owner에게 전송
	Location : https://client/callback?code=3 형태로 전송
	
	Location이란? 응답할 때 header값으로 Location을 줌 = redirection
	
	웹 브라우저에게 위의 주소로 이동하라고 명령함(Resource Server가 Resource Owner의 웹브라우저에게)
	웹 브라우저는 은밀하게 이 주소로 이동
	
	그럼 code=3에 의해 client는 authorization code = 3을 알게 됨
	
	그 후 Client는 Client Secret과 authorization code값을 조합하여 Resource Server에 전송
	
	Resource Server는 Authorization code값과 Client id, Client Secret, redirect uri 모두 확인한 후 모두 일치하면 다음 단계 진행
	
4. 액세스 토큰 발급
	Resource Server는 Authorization code값을 통해서 인증을 했기 때문에 코드값 지움
	
	액세스 토큰 생성 후 Client에게 액세스 토큰 값 응답 -> 클라이언트는 액세스 토큰 값 저장
	
	Resource Server는 액세스 토큰에 대응되는 user id와 허용된 기능을 찾은 후 같은 액세스 토큰을 가진 사용자에게 권한을 허용
	
	즉 Client가 액세스토큰 4를 들고 Resource Server로 접근하면 해당 토큰에 있는 user id에게 기능을 제공할 수 있는 권한을 줘라는 뜻



