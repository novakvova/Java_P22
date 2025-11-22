const changeTheme = (theme: string) => {
    const html = document.documentElement;
    html.classList.remove("light", "dark", "pink", "red", "ukraine", "dracula");
    html.classList.add(theme);
}

export default changeTheme;