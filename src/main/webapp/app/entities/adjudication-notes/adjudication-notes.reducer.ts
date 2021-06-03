import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudicationNotes, defaultValue } from 'app/shared/model/adjudication-notes.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATIONNOTES_LIST: 'adjudicationNotes/FETCH_ADJUDICATIONNOTES_LIST',
  FETCH_ADJUDICATIONNOTES: 'adjudicationNotes/FETCH_ADJUDICATIONNOTES',
  CREATE_ADJUDICATIONNOTES: 'adjudicationNotes/CREATE_ADJUDICATIONNOTES',
  UPDATE_ADJUDICATIONNOTES: 'adjudicationNotes/UPDATE_ADJUDICATIONNOTES',
  PARTIAL_UPDATE_ADJUDICATIONNOTES: 'adjudicationNotes/PARTIAL_UPDATE_ADJUDICATIONNOTES',
  DELETE_ADJUDICATIONNOTES: 'adjudicationNotes/DELETE_ADJUDICATIONNOTES',
  RESET: 'adjudicationNotes/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudicationNotes>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationNotesState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationNotesState = initialState, action): AdjudicationNotesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONNOTES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONNOTES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATIONNOTES):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATIONNOTES):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATIONNOTES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONNOTES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONNOTES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONNOTES):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATIONNOTES):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATIONNOTES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONNOTES):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATIONNOTES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONNOTES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONNOTES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATIONNOTES):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATIONNOTES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONNOTES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATIONNOTES):
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

const apiUrl = 'api/adjudication-notes';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudicationNotes> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATIONNOTES_LIST,
  payload: axios.get<IAdjudicationNotes>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudicationNotes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATIONNOTES,
    payload: axios.get<IAdjudicationNotes>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudicationNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATIONNOTES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudicationNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATIONNOTES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudicationNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONNOTES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudicationNotes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATIONNOTES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
