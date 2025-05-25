import { createRoot } from 'react-dom/client';

function NavigationBar() {
}

const domNode = document.getElementById('navigation');
const root = createRoot(domNode);
root.render(<NavigationBar />);