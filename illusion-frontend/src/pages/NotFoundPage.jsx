import { Link } from 'react-router-dom';
import { FaExclamationTriangle } from 'react-icons/fa';

const NotFoundPage = () => {
  return (
    <section className='flex items-center justify-center h-screen text-center'>
      <div>
        <FaExclamationTriangle className='text-yellow-400 text-6xl mb-4' />
        <h1 className='text-6xl font-bold mb-4'>404 Not Found</h1>
        <p className='text-xl mb-5'>This page does not exist</p>
        <Link
          to='/'
          className='text-white bg-indigo-700 hover:bg-indigo-900 rounded-md px-3 py-2 mt-4'
        >
          Go Back
        </Link>
      </div>
    </section>
  );
};

export default NotFoundPage;


// TODO
