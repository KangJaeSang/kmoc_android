# K-MOOC 강좌정보 서비스

주제 </br>
K-MOOC에서 제공하는 강좌의 목록과 상세 내용을 제공하는 서비스를 완성하세요. </br></br>
요구사항 </br>
강좌 목록을 표시하세요 </br>
각 강좌는 썸네일, 운영기관, 운영기간 정보로 구성됩니다. </br>
Pull to refresh 가 가능합니다. </br>
무한 스크롤방식으로 목록이 추가로 보여집니다. </br>

강좌 상세를 표시하세요 </br>
강좌 이미지를 표시합니다. </br>
강좌 번호, 분류, 운영기관, 교수정보, 운영기간, 추가 상세정보로 구성됩니다. </br>
추가 상세정보는 웹뷰로 표현됩니다. </br>

베이스코드 설명
Android
KmoocListActivity, KmoocDetailActivity로 구성됩니다. </br>
각 Activity는 해당 ViewModel이 제공됩니다.
data fetch는 구현된 상태로 제공됩니다.

KmoocRepository::list() : 첫 목록 불러오기 </br>
KmoocRepository::next() : 다음 페이지 불러오기 </br>
KmoocRepository::detail() : 상세 정보 불러오기 </br>

fetch된 데이터는 Lecture, LectureList 모델을 사용하세요 </br>
Lecture 모델의 필드 설명은 주석으로 작성되어 있습니다. </br>
java.util.Date 타입을 파싱하고 포맷팅할 때 .utils.DateUtil 을 사용하세요. </br
url로 이미지를 로딩할 때 .network.ImageLoader 를 사용하세요.


유의사항
베이스코드에 데이터 요청을 위한 인증키가 포함되어 있으나, 
오픈 API에 일일 트래픽 제한이 있어 데이터 요청이 실패할 수 있습니다.  </br>
만약 트래픽 제한에 걸릴 경우 아래의 방법을 통해 새로운 인증키를 발급받아 사용할 수 있습니다.

공공데이터포털로 이동 (https://www.data.go.kr/index.do) </br>
회원가입 또는 네이버/카카오 로그인 </br>
K-MOOC 강좌정보 API 페이지 접속 (https://www.data.go.kr/data/15042355/openapi.do) </br>
활용신청 - 관련정보 입력하여 신청 (활용목적은 '기타-프로그래밍 과제' 등으로 적절히 입력해주시면 됩니다) </br>
승인 후 발급받은 인증키를 사용하여 API 요청(초기 코드 내에 포함된 인증키를 새로 발급받은 인증키로 교체) </br>


