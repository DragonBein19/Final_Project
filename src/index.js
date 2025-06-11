import React from 'react';
import { createRoot } from 'react-dom/client';

function NavigationBar() {
  return <nav>Navigation here</nav>;
}

function SignUp() {
  return <div>Sign Up Form</div>;
}

const domNode = document.getElementById('navigation');
if (domNode) {
  const root = createRoot(domNode);
  root.render(<NavigationBar />);
}

const rootNode = document.getElementById('root');
if (rootNode) {
  const root = createRoot(rootNode);
  root.render(<SignUp />);
}
