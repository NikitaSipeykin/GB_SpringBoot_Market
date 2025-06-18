function onClickAddToCart(button) {
    button.disabled = true;
    fetch(window.location.origin + "/cart/product/" + button.dataset.productId, {method: "POST"})
        .then(response => response.json())
        .then(body => renderCart(body))
        .finally(() => button.disabled = false);
}

function onClickRemoveFromCart(button) {
    button.disabled = true;
    fetch(window.location.origin + "/cart/product/" + button.dataset.productId, {method: "DELETE"})
        .then(response => response.json())
        .then(body => renderCart(body))
        .finally(() => button.disabled = false);
}

function getAndRenderCart() {
    fetch(window.location.origin + "/cart")
        .then(response => response.json())
        .then(body => renderCart(body));
}

function displayCart() {
    document.getElementById("shoppingCart").classList.toggle('d-none');
}

function renderCart(cart) {
    var cartInnerHtml = `
    <div class="shopping-cart-header">
        <i class="fa fa-shopping-cart cart-icon"></i><span class="badge rounded-pill bg-secondary">${cart.items.length}</span>
        <div class="shopping-cart-total">
            <span class="lighter-text">Итог:</span>
            <span class="main-color-text">${cart.price} ₽</span>
        </div>
    </div>
    <ul class="shopping-cart-items">`;
    cart.items.forEach(item => {
        cartInnerHtml += `
        <li>
            <span class="item-name">
                ${item.title}
                <i class="fas fa-minus cart-control" data-product-id=\"${item.productId}\" onclick="onClickRemoveFromCart(this)"></i>
                <i class="fas fa-plus cart-control" data-product-id=\"${item.productId}\" onclick="onClickAddToCart(this)"></i>
            </span>
            <span class="item-price">${item.price} ₽</span>
            <span class="item-quantity">${item.count}</span>
        </li>`;
    });
    cartInnerHtml += `
    </ul>
    <a href="/order" class="btn cart-button w-100">Оформить заказ</a>`;
    document.getElementById("shoppingCart").innerHTML = cartInnerHtml;
}
