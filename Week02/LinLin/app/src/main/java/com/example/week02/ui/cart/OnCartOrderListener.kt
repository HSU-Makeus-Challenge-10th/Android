package com.example.week02.ui.cart

// CartFragment에서 발생한 이벤트를 Activity로 전달하기 위한 인터페이스
interface OnCartOrderListener {
    // "주문하기 버튼 클릭" 이벤트를 전달하는 함수
    fun onOrderClick()
}
