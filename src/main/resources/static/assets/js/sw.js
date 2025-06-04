self.addEventListener('push', event => {
  const data = event.data.json();
  console.log('Push event received:', data);
  self.registration.showNotification(data.title, {
    body: data.message,
    icon: 'https://cdn.pixabay.com/photo/2015/12/16/17/41/bell-1096280_1280.png'
  });
});