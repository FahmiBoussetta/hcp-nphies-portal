import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INote, defaultValue } from 'app/shared/model/note.model';

export const ACTION_TYPES = {
  FETCH_NOTE_LIST: 'note/FETCH_NOTE_LIST',
  FETCH_NOTE: 'note/FETCH_NOTE',
  CREATE_NOTE: 'note/CREATE_NOTE',
  UPDATE_NOTE: 'note/UPDATE_NOTE',
  PARTIAL_UPDATE_NOTE: 'note/PARTIAL_UPDATE_NOTE',
  DELETE_NOTE: 'note/DELETE_NOTE',
  RESET: 'note/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INote>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type NoteState = Readonly<typeof initialState>;

// Reducer

export default (state: NoteState = initialState, action): NoteState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NOTE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NOTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_NOTE):
    case REQUEST(ACTION_TYPES.UPDATE_NOTE):
    case REQUEST(ACTION_TYPES.DELETE_NOTE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_NOTE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_NOTE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NOTE):
    case FAILURE(ACTION_TYPES.CREATE_NOTE):
    case FAILURE(ACTION_TYPES.UPDATE_NOTE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_NOTE):
    case FAILURE(ACTION_TYPES.DELETE_NOTE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NOTE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_NOTE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_NOTE):
    case SUCCESS(ACTION_TYPES.UPDATE_NOTE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_NOTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_NOTE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/notes';

// Actions

export const getEntities: ICrudGetAllAction<INote> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_NOTE_LIST,
  payload: axios.get<INote>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<INote> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NOTE,
    payload: axios.get<INote>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<INote> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NOTE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INote> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NOTE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<INote> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_NOTE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<INote> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NOTE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
