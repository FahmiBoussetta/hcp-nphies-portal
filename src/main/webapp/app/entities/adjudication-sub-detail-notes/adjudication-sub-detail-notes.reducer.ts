import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAdjudicationSubDetailNotes, defaultValue } from 'app/shared/model/adjudication-sub-detail-notes.model';

export const ACTION_TYPES = {
  FETCH_ADJUDICATIONSUBDETAILNOTES_LIST: 'adjudicationSubDetailNotes/FETCH_ADJUDICATIONSUBDETAILNOTES_LIST',
  FETCH_ADJUDICATIONSUBDETAILNOTES: 'adjudicationSubDetailNotes/FETCH_ADJUDICATIONSUBDETAILNOTES',
  CREATE_ADJUDICATIONSUBDETAILNOTES: 'adjudicationSubDetailNotes/CREATE_ADJUDICATIONSUBDETAILNOTES',
  UPDATE_ADJUDICATIONSUBDETAILNOTES: 'adjudicationSubDetailNotes/UPDATE_ADJUDICATIONSUBDETAILNOTES',
  PARTIAL_UPDATE_ADJUDICATIONSUBDETAILNOTES: 'adjudicationSubDetailNotes/PARTIAL_UPDATE_ADJUDICATIONSUBDETAILNOTES',
  DELETE_ADJUDICATIONSUBDETAILNOTES: 'adjudicationSubDetailNotes/DELETE_ADJUDICATIONSUBDETAILNOTES',
  RESET: 'adjudicationSubDetailNotes/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAdjudicationSubDetailNotes>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AdjudicationSubDetailNotesState = Readonly<typeof initialState>;

// Reducer

export default (state: AdjudicationSubDetailNotesState = initialState, action): AdjudicationSubDetailNotesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILNOTES):
    case REQUEST(ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILNOTES):
    case REQUEST(ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILNOTES):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILNOTES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES):
    case FAILURE(ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILNOTES):
    case FAILURE(ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILNOTES):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILNOTES):
    case FAILURE(ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILNOTES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILNOTES):
    case SUCCESS(ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILNOTES):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILNOTES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILNOTES):
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

const apiUrl = 'api/adjudication-sub-detail-notes';

// Actions

export const getEntities: ICrudGetAllAction<IAdjudicationSubDetailNotes> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES_LIST,
  payload: axios.get<IAdjudicationSubDetailNotes>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAdjudicationSubDetailNotes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADJUDICATIONSUBDETAILNOTES,
    payload: axios.get<IAdjudicationSubDetailNotes>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdjudicationSubDetailNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADJUDICATIONSUBDETAILNOTES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdjudicationSubDetailNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADJUDICATIONSUBDETAILNOTES,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAdjudicationSubDetailNotes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ADJUDICATIONSUBDETAILNOTES,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdjudicationSubDetailNotes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADJUDICATIONSUBDETAILNOTES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
