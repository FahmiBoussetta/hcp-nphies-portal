import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './attachment.reducer';
import { IAttachment } from 'app/shared/model/attachment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AttachmentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  const loading = useAppSelector(state => state.attachment.loading);
  const updating = useAppSelector(state => state.attachment.updating);
  const updateSuccess = useAppSelector(state => state.attachment.updateSuccess);

  const handleClose = () => {
    props.history.push('/attachment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...attachmentEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...attachmentEntity,
          language: 'AR',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.attachment.home.createOrEditLabel" data-cy="AttachmentCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.attachment.home.createOrEditLabel">Create or edit a Attachment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="attachment-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.attachment.contentType')}
                id="attachment-contentType"
                name="contentType"
                data-cy="contentType"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.attachment.title')}
                id="attachment-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.attachment.language')}
                id="attachment-language"
                name="language"
                data-cy="language"
                type="select"
              >
                <option value="AR">{translate('hcpNphiesPortalApp.LanguageEnum.AR')}</option>
                <option value="EN">{translate('hcpNphiesPortalApp.LanguageEnum.EN')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.attachment.isData')}
                id="attachment-isData"
                name="isData"
                data-cy="isData"
                check
                type="checkbox"
              />
              <ValidatedBlobField
                label={translate('hcpNphiesPortalApp.attachment.dataFile')}
                id="attachment-dataFile"
                name="dataFile"
                data-cy="dataFile"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.attachment.url')}
                id="attachment-url"
                name="url"
                data-cy="url"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.attachment.attachmentSize')}
                id="attachment-attachmentSize"
                name="attachmentSize"
                data-cy="attachmentSize"
                type="text"
              />
              <ValidatedBlobField
                label={translate('hcpNphiesPortalApp.attachment.hash')}
                id="attachment-hash"
                name="hash"
                data-cy="hash"
                openActionLabel={translate('entity.action.open')}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/attachment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AttachmentUpdate;
