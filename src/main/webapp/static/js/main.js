const addButtonList = document.getElementsByClassName("add-button");

function addButton() {
    for (const addButton of addButtonList){
        addButton.addEventListener("click", () => {
            const id = addButton.id;
            const user = sessionStorage
            postData(`/cart?id=${id}`);
        });
    }

    async function postData(url = '') {
        await fetch(url, {
            method: 'POST'
        });
    }
}

function deleteButton(){
    const deleteButtonList = document.getElementsByClassName("remove");
    for (const deleteButton of deleteButtonList){
        deleteButton.addEventListener("click", () => {
            const id = deleteButton.id;
            deleteData(`/cart?id=${id}`).then(r => location.reload());
        })
    }

    async function deleteData(url = '') {
        const response = await fetch(url, {
            method: 'DELETE'
        });
        if (!response.ok){

        }
    }
}

function activeCategory() {
    const categories = document.getElementsByClassName("category");
    const url = document.URL.slice(20, 24);
    for (let category of categories) {
        if (category.id.slice(0, 4) !== url && category.classList.contains("active")){
            category.classList.remove("active")
        }
        if (category.id.slice(0, 4) === url) {
            category.classList.add("active")
        }
    }

}

addButton();
deleteButton();
activeCategory();

setupEventListenersForPopup();

function setupEventListenersForPopup() {
    for (const addButtonListElement of addButtonList) {
        addButtonListElement.addEventListener("click", showPopup);
    }
}

function showPopup(e) {
    let button = e.target;
    let id = button.id;
    let popupToShow = document.getElementById("popup"+id);
    popupToShow.classList.toggle("show");
    setTimeout((event) => hidePopup(popupToShow), 2200);
}

function hidePopup(popup) {
    popup.classList.toggle("show");
}