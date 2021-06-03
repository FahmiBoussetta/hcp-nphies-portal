import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './attachment.reducer';
import { IAttachment } from 'app/shared/model/attachment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAttachmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AttachmentUpdate = (props: IAttachmentUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { attachmentEntity, loading, updating } = props;

  const { dataFile, dataFileContentType, hash, hashContentType } = attachmentEntity;

  const handleClose = () => {
    props.history.push('/attachment');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...attachmentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
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
            <AvForm model={isNew ? {} : attachmentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="attachment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="attachment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="contentTypeLabel" for="attachment-contentType">
                  <Translate contentKey="hcpNphiesPortalApp.attachment.contentType">Content Type</Translate>
                </Label>
                <AvField id="attachment-contentType" data-cy="contentType" type="text" name="contentType" />
              </AvGroup>
              <AvGroup>
                <Label id="titleLabel" for="attachment-title">
                  <Translate contentKey="hcpNphiesPortalApp.attachment.title">Title</Translate>
                </Label>
                <AvField id="attachment-title" data-cy="title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="languageLabel" for="attachment-language">
                  <Translate contentKey="hcpNphiesPortalApp.attachment.language">Language</Translate>
                </Label>
                <AvInput
                  id="attachment-language"
                  data-cy="language"
                  type="select"
                  className="form-control"
                  name="language"
                  value={(!isNew && attachmentEntity.language) || 'AR'}
                >
                  <option value="AR">{translate('hcpNphiesPortalApp.LanguageEnum.AR')}</option>
                  <option value="EN">{translate('hcpNphiesPortalApp.LanguageEnum.EN')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="isDataLabel">
                  <AvInput id="attachment-isData" data-cy="isData" type="checkbox" className="form-check-input" name="isData" />
                  <Translate contentKey="hcpNphiesPortalApp.attachment.isData">Is Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="dataFileLabel" for="dataFile">
                    <Translate contentKey="hcpNphiesPortalApp.attachment.dataFile">Data File</Translate>
                  </Label>
                  <br />
                  {dataFile ? (
                    <div>
                      {dataFileContentType ? (
                        <a onClick={openFile(dataFileContentType, dataFile)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {dataFileContentType}, {byteSize(dataFile)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('dataFile')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_dataFile" data-cy="dataFile" type="file" onChange={onBlobChange(false, 'dataFile')} />
                  <AvInput type="hidden" name="dataFile" value={dataFile} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="urlLabel" for="attachment-url">
                  <Translate contentKey="hcpNphiesPortalApp.attachment.url">Url</Translate>
                </Label>
                <AvField id="attachment-url" data-cy="url" type="text" name="url" />
              </AvGroup>
              <AvGroup>
                <Label id="attachmentSizeLabel" for="attachment-attachmentSize">
                  <Translate contentKey="hcpNphiesPortalApp.attachment.attachmentSize">Attachment Size</Translate>
                </Label>
                <AvField
                  id="attachment-attachmentSize"
                  data-cy="attachmentSize"
                  type="string"
                  className="form-control"
                  name="attachmentSize"
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="hashLabel" for="hash">
                    <Translate contentKey="hcpNphiesPortalApp.attachment.hash">Hash</Translate>
                  </Label>
                  <br />
                  {hash ? (
                    <div>
                      {hashContentType ? (
                        <a onClick={openFile(hashContentType, hash)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {hashContentType}, {byteSize(hash)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('hash')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_hash" data-cy="hash" type="file" onChange={onBlobChange(false, 'hash')} />
                  <AvInput type="hidden" name="hash" value={hash} />
                </AvGroup>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/attachment" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  attachmentEntity: storeState.attachment.entity,
  loading: storeState.attachment.loading,
  updating: storeState.attachment.updating,
  updateSuccess: storeState.attachment.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AttachmentUpdate);
