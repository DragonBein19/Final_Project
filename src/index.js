import React from 'react';
import { createRoot } from 'react-dom/client';

function NavigationBar() {
}

const domNode = document.getElementById('navigation');
const root = createRoot(domNode);
root.render(<NavigationBar />);

const rootNode = document.getElementById('root');
if (rootNode) {
  const root = createRoot(rootNode);
  root.render(<SignUp />);
}

