function main(){
  var tl_canvas = document.getElementById('top_left');
  var tl_context = tl_canvas.getContext('2d');
  tl_context.fillStyle = 'rgba(0, 65, 65, 1.0)';
  tl_context.fillRect(0, 0, 200, 60);
  tl_context.fillStyle = 'rgba(65, 0, 0, 1.0)';
  tl_context.fillRect(200, 0, 230, 60);
  tl_context.font = 'italic 30pt Georgia';
  tl_context.fillStyle = 'rgba(255, 215, 0, 1.0)';
  tl_context.fillText('RoleCall', 10, 40);
  tl_context.fillStyle = 'rgba(192, 192, 192, 1.0)';
  tl_context.fillText('Team Silver', 210, 40);
}
